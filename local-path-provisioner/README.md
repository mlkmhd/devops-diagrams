# Local Disk Storage for Kubernetes
A guid for installation and usage of local-path-provisioner in Kubernetes for high performance applications

<p align="center">
  <img src="pictures/local-storage-introduction.png?raw=true" />
</p>

# How it works?
<p align="center">
  <img src="pictures/local-storage-pv-and-pvc.png?raw=true" />
</p>
the following steps will happen when you create a PVC on kubernetes:

- local-path-provisioner watches the PVCs continuesly and waiting for a new one
- A volume on the worker node where the pod exists will be created (normally under /var/volumes/ directory, you can change it if you want)
- local-path-provisioner will create a PV for volume
- the volume will attach to the PV
- the PV will be bound to the PVC
