# DevOps Big Picture (on-premises)
A comprehensive overview of DevOps best practices and tools for on-premises environments is needed. Although there are numerous DevOps tools and practices available, I have been unable to find a comprehensive overview. Therefore, I have made an effort to delve deeper into the DevOps ecosystem and study best practices, aiming to create an extensive overview that can help assess our current on-premises state and guide our future actions.

(I used the [draw.io](https://draw.io) website to create these files. You can import the `.xml` files from this repository into the [draw.io](https://draw.io) website and make any desired changes)

<p align="center">
  <img src="pictures/overall.png?raw=true" />
</p>


## CI (Continues Integration)
the CI part of this diagram includes the following items:
<p align="center">
  <img src="pictures/ci.png?raw=true" />
</p>

- Code Repository: I have chosen GitLab as the source control and code repository in the diagram due to its user-friendly interface for repository management. It allows hierarchical grouping and provides excellent control over team structures.
- Build Tool: GitLab has been selected as the build tool in the diagram because of its extensive features for writing pipelines as code and templating.
- Automated Test: While there are numerous end-to-end test frameworks available, Cypress is currently the most popular one in the community.
- Artifact Repository: I have utilized Harbor as the artifact repository for storing docker images or helm charts. Although there are cloud options available, Harbor is recommended for air-gapped environments where such tools are necessary.

## CD (Continues Delivery)
I have separated the CD repository from the source code repository to accommodate the requirement of multiple environments for multiple clients. However, if you do not have multiple environments for each of your products, you can combine them into a single repository.
<p align="center">
  <img src="pictures/cd.png?raw=true" />
</p>

- Infrastructure as Code: To provision infrastructure (VM) and platform (Kubernetes), it is advisable to use a tool like Terraform for easy creation. While there are other options like Ansible or Puppet available, these tools do not support the declarative format. I highly recommend using Terraform with GitLab for storing the current state of your IAC.
- Deployment Service: I have utilized GitLab as a deployment service to store environment configuration files for each application. You can create a Git repository within GitLab to store your config files and define your pipeline for deploying a Helm chart to the Kubernetes cluster. Although there are other options like Spinnaker available, I find it to be overly complex with many features that may not be necessary.

## CM (Continues Monitoring)
<p align="center">
  <img src="pictures/cm.png?raw=true" />
</p>

- Metric Server: I have utilized Prometheus as the metric server to collect and store metrics from applications, platforms, and infrastructure.
- Logs Server: I have chosen the ELK stack because of its popularity within the community for collecting and storing logs. It provides extensive capabilities for creating analytics dashboards based on the collected logs.
- Tracing Server: I have opted for Jaeger as the tracing server. While Zipkin is another alternative, I personally recommend Jaeger due to its larger and more active community. It is also a newer project.
- Infrastructure Monitoring: There are numerous tools available for infrastructure monitoring, each with its own set of advantages and disadvantages. However, I have utilized Zabbix as it is an open-source project with comprehensive monitoring capabilities. It is an agent-based tool, although there are agent-less alternatives available. Some companies choose to use SolarWinds as an alternative.
- Auto-Scaler: The Keda project is specifically designed for auto-scaling pods based on different metrics in Kubernetes. It supports various types of applications and can collect metrics from them to enable auto-scaling. Additionally, there are other tools available for auto-scaling infrastructure and platform resources (such as VMs or Kubernetes worker nodes) based on cloud metrics.
