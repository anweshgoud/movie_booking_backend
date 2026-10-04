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
    }
}
