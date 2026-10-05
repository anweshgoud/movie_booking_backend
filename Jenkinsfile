pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    mvn test
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t anweshanthati/movie_booking_backend:${GIT_COMMIT} .'
            }
        }
    }
}
