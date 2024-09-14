buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Plugin Safe Args para navegação
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
    }
}

allprojects {
    // Não é mais necessário declarar repositórios aqui
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
