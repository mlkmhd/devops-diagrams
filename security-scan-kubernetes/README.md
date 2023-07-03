# Security Check Before and After Deploying to Kubernetes

As depicted in the image below, it is crucial to incorporate a security check within your CI/CD pipeline. Additionally, performing a security check after deploying to a Kubernetes cluster is equally important. This is because Kubernetes administrators have the ability to modify manifest files on the fly. Therefore, it is essential to have a security check dashboard where a security analyzer can periodically evaluate the deployed environment.

<p align="center">
  <img src="pictures/security-scan-after-deployment.png?raw=true" />
</p>
