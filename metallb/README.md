# Kubernetes LoadBalancer service for On-Premises
You can define a `LoadBalancer` service in Kubernetes even in an On-Premises environment using MetalLB.

as you can see in the image below, the nodeport service is accessible through all worker nodes but the client should load balance requests on himself:
<p align="center">
  <img src="pictures/metallb-network-diagram-nodeport.drawio.png?raw=true" />
</p>

But if you using `LoadBalancer` service, you can define a `VIP` for your service and the client does not to know ther worker's machine IPs. 
<p align="center">
  <img src="pictures/metallb-network-diagram-loadbalancer.drawio.png?raw=true" />
</p>
