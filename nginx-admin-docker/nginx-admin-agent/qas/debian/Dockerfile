FROM ubuntu:16.04
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk unzip sudo nginx python-setuptools
RUN easy_install supervisor
COPY build/supervisord.conf /etc/supervisord.conf
RUN useradd nginx-agent -r
RUN useradd nginx -r
RUN chmod 640 /etc/sudoers
RUN printf 'nginx-agent ALL=(ALL) NOPASSWD:/usr/sbin/nginx,/usr/bin/pgrep nginx,/usr/bin/killall nginx\n' >> /etc/sudoers
RUN chmod 440 /etc/sudoers
RUN mkdir -p /opt/downloads
COPY build/nginx-agent-bin.zip /opt/downloads/nginx-agent.zip
RUN unzip /opt/downloads/nginx-agent.zip -d /opt
RUN chmod -R 755 /opt/nginx-agent
RUN chown -R nginx-agent:nginx-agent /opt/nginx-agent
EXPOSE 80
EXPOSE 3000
EXPOSE 3443
CMD ["/usr/local/bin/supervisord"]