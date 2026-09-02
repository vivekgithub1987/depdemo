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

		stage('Deploy To EC2') {
		    steps {
		        bat '''
		        ssh -i C:\\ProgramData\\Jenkins\\.ssh\\lms-key.pem -o StrictHostKeyChecking=no ec2-user@13.61.24.31 "docker stop depdemo >/dev/null 2>&1 || true; docker rm depdemo >/dev/null 2>&1 || true; docker pull vpdocker2025/depdemo:latest; docker run -d -p 8080:8080 --name depdemo vpdocker2025/depdemo:latest; docker ps"
		        '''
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