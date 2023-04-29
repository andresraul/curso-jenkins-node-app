job('Aplicacion Node.js Docker DSL') {
    description('Aplicación Node JS Docker DSL para el curso de Jenkins')
    scm {
        git('https://github.com/andresraul/curso-jenkins-node-app.git', 'main') { node ->
            node / gitConfigName('Andres Mateo')
            node / gitConfigEmail('andresraul@gmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    wrappers {
        nodejs('nodejs14')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('andresrmateo/nodejsapp') //Nombre del repositorio
            tag('${GIT_REVISION,length=7}') // Nombre de la etiqueta. De esta forma, toma el nombre único que le prevee git
            registryCredentials('docker-hub') // Establecemos el nombre de las credenciales globales de docker hub que debemos configurar previamente
            forcePull(false) //force pull se encarga de actualizar la imagen antes de contruirla(si está en true), para asegurarse que es la correcta
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
