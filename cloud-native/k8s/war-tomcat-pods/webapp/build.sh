# build java
mvn clean package

# build image
docker image build --platform linux/x86_64 -t pushyzheng/webapp-example .
