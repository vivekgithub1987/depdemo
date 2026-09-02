pipeline {

    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Version') {
            steps {
                bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" version'
            }
        }

        stage('Docker Build') {
            steps {
                bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" build -t depdemo:latest .'
            }
        }

        stage('Docker Tag') {
            steps {
                bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" tag depdemo:latest vpdocker2025/depdemo:latest'
            }
        }

        stage('Docker Images') {
            steps {
                bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" images'
            }
        }

        stage('Docker Login & Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" login -u %DOCKER_USER% -p %DOCKER_PASS%'
                    bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" push vpdocker2025/depdemo:latest'
                }
            }
        }

		stage('Test SSH') {
		    steps {
		        sshagent(credentials: ['ec2-ssh-key']) {
		            bat '''
		            set SSH_AUTH_SOCK
		            set SSH_AGENT_PID
		            ssh-add -L
		            ssh -vvv -o StrictHostKeyChecking=no ec2-user@13.61.24.31 hostname
		            '''
		        }
		    }
		}
    }

    post {
        success {
            echo 'CI/CD Pipeline Executed Successfully'
        }

        failure {
            echo 'Pipeline Failed'
        }
    }
}