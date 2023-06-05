pipeline {
    agent any
    tools {
        jdk 'java-jdk17'
        mvn 'jenkins-maven'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/septianrezaandrianto/rnd-spring-boot-3.git/'
            }
        }
        stage('Sonar Analysis') {
            steps {
                sh 'mvn clean package'
                sh ''' mvn clean verify sonar:sonar -Dsonar.projectKey=rnd-springboot-3 -Dsonar.projectName='rnd-springboot-3' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_9591bb4778470b26b47be54ea13b0a1dc39f0dd4 '''
            }
        }
    }
}