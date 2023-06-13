pipeline {
    agent any
    stages {
        stage('Git Checkout') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/septianrezaandrianto/rnd-spring-boot-3.git/'
            }
        }
        stage('SonarQube analysis') {
            steps {
//                 withSonarQubeEnv('SonarQube') {
                withSonarQubeEnv(credentialsId: 'sqp_28d466f9742f040fa0646d44c121ca5c30e6e76d', installationName: 'SonarQube') {
                    bat 'mvn clean package'
                    bat ''' mvn clean verify sonar:sonar -Dsonar.projectKey=rnd-springboot-3.0 -Dsonar.projectName='rnd-springboot-3.0' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_28d466f9742f040fa0646d44c121ca5c30e6e76d '''
                }
            }
        }
        stage("Quality gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}