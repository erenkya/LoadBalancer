(1)
We have used one static one dynamic loadbalancing strategie. For static one we want to use ip hash but in our project we are using "localhost" so we implemented it as port hash. In dynamic load balancer we used resource based load balancing strategy. We obserev de load levels of the servers in the loadbalancer and we canalize the packets the server which has least load level.

(2)
We used UDP in our project. In our project we used Scanner object to take their decisions from user. And also we used DatagramSocket , InetAddress and DatagramPacket for providing sending packets and messages between Client , LoadBalancer and two Servers.

(3)
We tried to use multi mechanism in our project , especially we want to use TCP for video broadcast but we faced with too much improper and discordant values and classes. Also message synchronization is very hard if you want to provide nonlinear messaging between client , loadbalancer and server.

(4)
In testing we dsetermined the input groups which are lead to difrent outputs or interactions in the program. So for every group we test one input.

(5)
User have to run every java file in the project . In order to run Server2 , Server1 , LoadBalancer and Client recommended but project can be runned in any order.
