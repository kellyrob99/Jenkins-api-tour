package org.kar.hudson.api

/**
 * @author Kelly Robinson
 */
class UDPApi
{
    static final TIMEOUT = 5000
    def sendUDP(rootUrl)
    {
        def data = "anything".getBytes("ASCII")
        def addr = InetAddress.getByName(rootUrl)
        def port = 33848
        def packet = new DatagramPacket(data, data.length, addr, port)
        def socket = new DatagramSocket()
        socket.send(packet)
        socket.setSoTimeout(TIMEOUT) // block for no more than 30 seconds
        def buffer = new byte[4096]
        def response = new DatagramPacket(buffer, buffer.length)
        socket.receive(response)
        new String(response.data, 0, response.length)
    }
}
