FROM centos:7
RUN yum -y update
RUN yum -y install openssh-server
RUN ssh-keygen -A 
RUN echo root:123456 | chpasswd
EXPOSE 22
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/sbin/sshd", "-D"]