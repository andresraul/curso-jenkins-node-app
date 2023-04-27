job('Aplicacion Node.js DSL') {
    description('Aplicación Node JS DSL para el curso de Jenkins')
    scm {//Configuramos el repositorio de github
        git('https://github.com/andresraul/curso-jenkins-node-app.git', 'main') { node ->
            node / gitConfigName('Andres Mateo')
            node / gitConfigEmail('andresraul@gmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *') //Configuramos in triguer que se ejecuta cada 7 minutos y si hay cambios en el repositorio vuelve a ejecutar el build
    }
    wrappers {
        nodejs('nodejs') //Definimos el node instalado previamente que queremos utilizar
    }
    steps {
        shell("npm install") //Instalamos los paquetes de node
    }
    publishers {
	slackNotifier {//Configuramos slack
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
