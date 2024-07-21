job('example') {
    // General configuration
    description('A Jenkins job to build and run a Gradle project from GitHub.')
    keepDependencies(false)

    // Configure SCM to checkout the repository from GitHub
    scm {
        git {
            remote {
                url('https://github.com/Ach712/gradle.git')
            }
            branch('*/main')
        }
    }

    // Define the steps to build and run the project
    steps {
        batchFile('gradle build')
        batchFile('gradle run')
    }
}
