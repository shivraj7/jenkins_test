pipeline {
    agent any

    stages {
        stage('Clone riscv-yocto-catapult') {
            steps {
                step($class: 'WsCleanup')
                sh 'export CATAPULT_SDK_TOPDIR=/opt/imgtec/catapult-sdk_1.8.1/'
                sh 'git clone git@github.imgtec.org:imaginationtech/riscv-yocto-catapult-11.git'
                sh 'pwd'
                sh 'ls'
                dir('/home/spatil/jenkins/jenkins_home/workspace/pipeline-riscv-yocto-catapult/riscv-yocto-catapult-11') {
                    sh 'pwd'
                    sh 'git checkout develop'
                }
            }
        }
        stage('Setup the external repositories') {
            steps {
                dir('/home/spatil/jenkins/jenkins_home/workspace/pipeline-riscv-yocto-catapult/riscv-yocto-catapult-11') {
                    sh 'pwd'
                    sh './third_party/setup-repo.sh'
                }
            }
        }
        stage('Run test script') {
            steps {
                dir('/home/spatil/jenkins/jenkins_home/workspace/pipeline-riscv-yocto-catapult/riscv-yocto-catapult-11') {
                    sh 'pwd'
                    sh 'INITRAMFS=0 ./meta-img-riscv/meta-riscv-catapult/setup.sh'
                }
            }
        }
        stage('Export bitbake ENV and build image') {
            steps {
                dir('/home/spatil/jenkins/jenkins_home/workspace/pipeline-riscv-yocto-catapult/riscv-yocto-catapult-11') {
                    sh 'pwd'
                    sh '''#!/bin/bash
                        echo "Hello from bash"
                        echo "Who I'm $SHELL"
                        source third_party/openembedded-core/oe-init-build-env
                        MACHINE=qemuriscv64 bitbake image-musl-dev-catapult
                    '''
                }
            }
        }
    }
}
