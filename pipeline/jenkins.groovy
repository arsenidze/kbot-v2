pipeline {
    agent any
    parameters {
        choice(name: 'OS', choices: ['linux', 'darwin', 'windows', 'all'], description: 'Pick OS')
        choice(name: 'ARCH', choices: ['amd64', 'arm64'], description: 'Pick ARCH')
    }
    stages {
        stage('Test') {
            steps {
                sh 'make test'
            }
        }
        stage('Build') {
            steps {
                sh "make image TARGETARCH=${params.ARCH}"
            }
        }
        stage('Push') {
            steps {
                script {
                    sh "make image TARGETARCH=${params.ARCH}"
                    docker.withRegistry(''. 'dockerhub') {
                        sh "make push TARGETARCH=${params.ARCH}"
                    }
                }
            }
        }
        stage('Clean') {
            steps {
                sh "make clean TARGETARCH=${params.ARCH}"
            }
        }
    }
}
