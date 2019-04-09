import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

@Volatile
private var deviceNum = 0
private val deviceIpMap = HashMap<Int, String>()
private const val ipCollectPort = 12306
private const val commandPort = 12307

private val cachedThreadPool = Executors.newCachedThreadPool()

private fun getSocketWriter(socket: Socket): PrintWriter {
    val outputStream = socket.getOutputStream()
    return PrintWriter(OutputStreamWriter(outputStream))
}

private fun getSocketReader(socket: Socket): BufferedReader {
    val inputStream = socket.getInputStream()
    return BufferedReader(InputStreamReader(inputStream))
}

class IpCollector(private val socket: Socket) : Runnable {
    override fun run() {
        deviceNum++
        println("第 $deviceNum 台设备接入")

        val writer = getSocketWriter(socket)
        writer.write("$deviceNum")
        writer.flush()
        socket.shutdownOutput()

        val reader = getSocketReader(socket)
        //格式：number号-ip地址
        val message = reader.readLine()
        val data = message.split("-")
        val number = data[0]
        val ipAddress = data[1]
        deviceIpMap[number.toInt()] = ipAddress
        println("已获取第 $number 台设备的ip地址")
        socket.shutdownInput()

        reader.close()
        writer.close()
        socket.close()
    }
}

fun runIpCollectTask() {
    print("开启线程池等待设备接入")
    Thread {
        val serverSocket = ServerSocket(ipCollectPort)
        while (true) {
            val socket = serverSocket.accept()
            synchronized(serverSocket) {
                cachedThreadPool.execute(IpCollector(socket))
            }
        }
    }.start()
}