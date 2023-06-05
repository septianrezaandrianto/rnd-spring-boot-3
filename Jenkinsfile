pipeline {
    agent any
    environment {
        MAVEN_ARGS=" -e clean install"
        registry = ""
        dockerContainerName = 'rnd-springboot-3.0'
        dockerImageName = 'rnd-springboot-3.0'
    }

    stages {
        stage('Build') {
            steps {
                withMaven(maven: 'jenkins-maven') {
                    sh "mvn ${MAVEN_ARGS}"
                }
            }
        }

        stage('clean container') {
            steps {
               sh 'docker ps -f name=${dockerContainerName} -q | xargs --no-run-if-empty docker container stop'
               sh 'docker container ls -a -fname=${dockerContainerName} -q | xargs -r docker container rm'
               sh 'docker images -q --filter=reference=${dockerImageName} | xargs --no-run-if-empty docker rmi -f'
            }
        }

        stage('docker-compose start') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }
}

// pipeline {
//     agent any
//
//     stages {
//         stage('Checkout') {
//             steps {
//                 // Checkout your source code from version control (e.g., Git)
//                 checkout scm
//             }
//         }
//
//         stage('Build') {
//             steps {
//                 // Build your Spring Boot application
//                 sh 'mvn clean package'
//             }
//         }
//
//         stage('Test') {
//             steps {
//                 // Run tests for your Spring Boot application
//                 sh 'mvn test'
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 // Build a Docker image for your Spring Boot application
//                 sh 'docker build -t your-image-name .'
//             }
//         }
//
//         stage('Deploy') {
//             steps {
//                 // Deploy the Docker image to a Docker registry or Kubernetes cluster
//                 sh 'docker push your-image-name'
//
//                 // Additional deployment steps (e.g., deploying to Kubernetes)
//                 // ...
//             }
//         }
//     }
// }