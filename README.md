# quickstart

Para iniciar um novo projeto à partir desse quick-start é necessário:
- Cloná-lo na máquina.
- Excluir a pasta do .git (ele fica oculto, necessário desocultar).
- Alterar os diretórios que tem o nome de quickstart para o nome do projeto.
- Alterar tudo o que encontrar (Edit -> Find -> Replace in path) como quickstart de configuraçao para o nome do projeto.

Para configurar o banco:
- Substituir nos arquivos de configuração de dbName e dbUrl para o nome do banco e url de acesso.
- Será utilizado o application.properties para config local e deployment.yml para produção.

Repositório git do novo projeto:
- entrar na pasta do projeto via terminal e seguir os passos abaixo:
```
git init
git add .
git commit -am "Versao inicial"
git remote add origin URL_REPOSITORIO
git remote -v
git push origin master
```

Obs: `git remote -v` (usado apenas para verificar se a URL esta correta).

Para configurar o job de continuous integration:
- Substituir nos arquivos sonar.properties e jenkinsfiles/* <APP_NAME> pelo nome do app (mesmo que aparece na url do github).
- Substituir nos arquivos jenkinsfiles/build_project.groovy <APP_ROLE> pelo nome da role aws que será utilizada em produção.
- Substituir nos arquivos jenkinsfiles/* <YOUR_GRADLE_TASK> pela task gradle que deve ser utilizada pro build/teste (ex: "clean test build jacocoTestReport")

Estrutura do projeto
- A aplicação é dividida basicamente em três pacotes: usecases, persistence e restapi

Usecases:
- Esse pacote é onde fica as regras de negócio da aplicação.
- Ele deve ser independente, enxergando apenas ele mesmo e disponibilizando interfaces para outros pacotes implementarem.

Persistence:
- Contém todas as entities do projeto.
- Implementa as interfaces da pasta repository do usecases.
- Essa implementação é usada para converter o model do usecases para a entity do banco de dados.
- Ele contém as configurações do banco de dados.

Restapi:
- Define as APIs tanto do próprio projeto quanto dos parceiros.
- Contém todas as configurações de API e utiliza nossa biblioteca de comunicação.

## Logging:
Mensagens de log, ao seguirem este padrão, estarão em formato JSON.  

Por isto é necessário incluir na aplicação a seguinte configuração de [logback.xml](usecases/src/main/resources/logback.xml) e instalar as dependências necessárias (ver [Logstash Logback Encoder](https://github.com/logstash/logstash-logback-encoder)).

Também É possível instalá-las via gradle:
```
dependencies {
    //...Suas dependências
    implementation group: 'net.logstash.logback', name:'logstash-logback-encoder', version:'6.1'
    implementation group: 'ch.qos.logback', name:'logback-classic', version: '1.2.3'
}
```

O arquivo [LoggerExtension.kt](usecases/src/main/kotlin/br/com/guiabolso/quickstart/misc/logging/LoggerExtension.kt) possui funções de extensão aplicadas a um Logger do Logback para facilitar o trabalho com MDC com propriedades variáveis e fixas, mas seu uso é opcional (desde que o log final esteja em JSON seguindo a configuração do logback.xml)

O Encoder utilizado para isto é o [Logstash Logback Encoder](https://github.com/logstash/logstash-logback-encoder).
A configuração básica está pronta no arquivo [logback.xml](usecases/src/main/resources/logback.xml) e recomenda-se fortemente não **alterar** o que já está lá, ainda que seja plenamente viável **adicionar** mais atributos para logging em caso isto seja necessário.

É importante ater-se ao fato de que **tudo** o que estiver no MDC será traduzido para **String** no log JSON, pois o encoder respeita o tipo de dados presente no atributo (que, no caso do MDC, só é possível adicionar propriedades tipo String), mas é possível mudar este comportamento com alguma configuração extra que pode (e deve) ser adicionada por aplicação, desta maneira:

Mudar esta linha
```
  <mdc />
```

Para:
```
  <mdc>
      <excludeMdcKeyName>nomeDoAtributoNoMDC</excludeMdcKeyName>
  </mdc>
```

E acrescentá-lo mais a diante, alterando este trecho:
```
    <pattern>
        <omitEmptyFields>true</omitEmptyFields>
        <!-- the pattern that defines what to include -->
        <pattern />
    </pattern>
```

Para:
```
    <pattern>
        <omitEmptyFields>true</omitEmptyFields>
        <!-- the pattern that defines what to include -->
        <pattern>
        {
            "nomedoCampoNoLog": "#asLong{%mdc{nomeDoAtributoNoMDC}}",
        }
        </pattern>
    </pattern>
```
Para mais informações, ler sobre [Composite Encoder Layout](https://github.com/logstash/logstash-logback-encoder#composite-encoderlayout)                
