pipeline {
    agent any
    tools {
        maven 'jenkins-maven'
    }

    stages {
        stage('Git Checkout') {
            steps {
//                 git changelog: false, poll: false, url: 'https://github.com/septianrezaandrianto/rnd-spring-boot-3.git/'
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/septianrezaandrianto/rnd-spring-boot-3']])
                bat 'mvn clean install'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn clean package'
                    bat ''' mvn clean verify sonar:sonar -Dsonar.projectKey=rnd-springboot-3.0 -Dsonar.projectName='rnd-springboot-3.0' -Dsonar.host.url=http://localhost:9000 '''
                }
            }
        }
        stage("Quality Gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker build -t septianreza/rnd-springboot-3.0 .'
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhub-password')]) {
                        bat 'docker login --username septianreza --password ${dockerhub-password}'
                    }
                    bat 'docker push septianreza/rnd-springboot-3.0'
                }
            }
        }

    }
    post {
        always {
            bat 'docker logout'
        }
    }
}