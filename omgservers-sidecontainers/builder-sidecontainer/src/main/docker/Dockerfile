FROM jenkins/jenkins:2.453-jdk17
USER root
RUN apt-get update && apt-get install -y lsb-release
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg
RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli
USER jenkins
RUN jenkins-plugin-cli --plugins "configuration-as-code docker-workflow job-dsl pipeline-utility-steps permissive-script-security ws-cleanup"
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Dpermissive-script-security.enabled=true"
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/jenkins.yaml
COPY src/main/docker/init.groovy.d /usr/share/jenkins/ref/init.groovy.d
COPY src/main/docker/jenkins.yaml /var/jenkins_home/jenkins.yaml
COPY src/main/docker/jobs /var/jobs
