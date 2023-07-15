# Managing multiple Kubernetes clusters using GitLab

you can manage multiple Kubernetes clusters in a central location using a GitLab server just by a simple group hierarchy. 

As you can see in the image below, you can create multiple groups within it and have a `KUBECONFIG` file as an environment variable in each group. This allows each project within the groups to inherit the `KUBECONFIG` variable from its parent:
<p align="center">
  <img src="pictures/gitlab-structure.png?raw=true" />
</p>
