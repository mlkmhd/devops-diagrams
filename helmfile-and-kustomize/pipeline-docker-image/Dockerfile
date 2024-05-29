FROM debian:11.8

ADD *.crt /usr/local/share/ca-certificates/
ADD install.sh /

RUN chmod +x /install.sh && /install.sh