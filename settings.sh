#!/bin/bash

# Create the settings.xml file from a template
cat <<EOF > settings.xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>nexus-deptinfo</id>
      <username>${MAVEN_ENT_USERNAME}</username>
      <password>${MAVEN_ENT_PASSWORD}</password>
    </server>
    <server>
      <id>nexus-deptinfo-snapshots</id>
      <username>${MAVEN_ENT_USERNAME}</username>
      <password>${MAVEN_ENT_PASSWORD}</password>
    </server>
    <server>
      <id>sonar</id>
      <username>${MAVEN_ENT_USERNAME}</username>
      <password>${MAVEN_ENT_PASSWORD}</password>
    </server>
  </servers>

  <mirrors>
    <mirror>
      <id>nexus-deptinfo</id>
      <mirrorOf>*</mirrorOf>
      <url>https://disc.univ-fcomte.fr/cr700-nexus/repository/maven-public/</url>
    </mirror>
  </mirrors>

  <pluginGroups>
    <pluginGroup>org.sonarsource.scanner.maven</pluginGroup>
  </pluginGroups>

  <profiles>
    <profile>
      <id>nexus-deptinfo-snapshots</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>nexus-deptinfo-snapshots</id>
          <url>https://disc.univ-fcomte.fr/cr700-nexus/content/groups/public</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
    <profile>
      <id>sonar</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <sonar.host.url>https://disc.univ-fcomte.fr/cr700-sonarqube</sonar.host.url>
      </properties>
    </profile>
  </profiles>
</settings>
EOF

# Move settings.xml to the .m2 directory of the runner's container
mv settings.xml /root/.m2/settings.xml

# Create the settings-security.xml file from a template
cat <<EOF > settings-security.xml
<settingsSecurity>
  <master>${MAVEN_MASTER_PASSWORD}</master>
</settingsSecurity>
EOF

# Move settings-security.xml to the .m2 directory of the runner's container
mv settings-security.xml /root/.m2/settings-security.xml
