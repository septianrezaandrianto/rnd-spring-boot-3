pipeline {
    agent any
    tools {
        maven 'jenkins-maven'
    }

    stages {
        stage('Git Checkout') {
            steps {
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
                    bat 'docker build -t rnd-springboot-3.0 .'
                }
            }
        }

        stage ('Docker Run') {
            steps {
                script {
                    bat 'docker run -d --name rnd-springboot-3.0 -p 8080:8080 rnd-springboot-3.0'
                }
            }
        }
//         stage('Docker Push') {
//             steps {
//                 script {
//                     withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhub-password')]) {
//                         bat ''' docker login -u septianreza -p "%dockerhub-password%" '''
//                     }
//                     bat 'docker push septianreza/rnd-springboot-3.0'
//                 }
//             }
//         }

    }
//     post {
//         always {
//             bat 'docker logout'
//         }
//     }
}