package org.kar.hudson.api

/**
 * @author Kelly Robinson
 */
class UDPApi
{
    static final int TIMEOUT = 5000
    static final int HUDSON_UDP_PORT = 33848

    /**
     * Query a given host to see if Hudson is installed
     * @param hostName hostName to contact with UDP packet
     * @return xml results of response as a String
     */
    def sendUDP(String hostName)
    {
        def data = "anything".getBytes("ASCII")
        def addr = InetAddress.getByName(hostName)

        def packet = new DatagramPacket(data, data.length, addr, HUDSON_UDP_PORT)
        def socket = new DatagramSocket()
        socket.send(packet)
        socket.setSoTimeout(TIMEOUT) // block for no more than 30 seconds

        def buffer = new byte[4096]
        def response = new DatagramPacket(buffer, buffer.length)
        socket.receive(response)
        new String(response.data, 0, response.length)
    }
}
