# Especialista Spring Rest
Curso da AlgaWorks Especialista Spring Rest  

Anotações:

2.7 - @Controller: Essa anotação diz que a classe responsável por receber requisições web.  
      @GetMapping: Define o método HTTP.  
      @ResponseBody: Diz que o retorno vai ser devolvido como resposta da requisição.  

2.11 - @Component: Anotação responsável por dizer ao Spring que aquele classe é um Bean, um componente, que pode ser injetada.  

2.13 - @Bean: Indica que o método anotado vai instanciar um objeto da classe que retorna no método. O nome do Bean é a assinatura do método.  
       @Configuration: Também um componente Spring, porém é usada para definir @Beans.  

2.14 - @Autowired: Anotação usada para definir o ponto de injeção. Pode ser usado em construtores, método Set, no próprio atributo.  
       @Autowired(required = false): Faz com que aquele atributo não seja obrigatório.  

2.17 - @Primary: Define o Bean como prioridade. Ideal para evitar ambiguações de Beans.  

2.18 - @Qualifier: Serve para identificar o componente.  

2.19 - @Retention: Diz em qual momento do ciclo de vida do programa a anotação vai ser aplicada.      
       @Retention(RetentionPolicy.RUNTIME): Propriedade que diz que a anotação pode ser lida em tempo de execução.  

2.20 - @Profile: Anotação que define o perfil (ambiente) em que o componente vai ser registrado. É obrigatório passar o nome do perfil ex: Profile(name = "prod"). O perfil
       é definido no application.properties ou application.yaml através do comando spring.profiles.active.  

2.21 - @PostConstruct: Anotação usada em métodos para executar o método logo após a inicialização de um Bean.  
       @PreDestroy: Anotação usada em métodos para executar o método antes do Bean ser destruído.  
       @Bean(initMethod = "nomeDoMetodo", destroyMethod="nomeDoMetodo") podemos definir na classe de configuração do Bean, usando essas chamadas dentro da anotação @Bean. Passamos o nome do método.  
       No component podemos também implementar as interfaces chamadas InitializingBean e DisposableBean que ao implementar os métodos das interface, tem o mesmo propósito do @PostConstruct e @PreDestroy respectivamente.  

2.22 - @EventListener: Anotação usada para escutar eventos na aplicação.  

2.25 - @Value: Anotação usada para pegar definições de propriedade no application.properties/yml. Passamos por parâmetro da anotação "${nome da propriedade do application}".  

2.26 - @ConfigurationProperties: Anotação em escopo de classe para pegar as configurações definidas no application.properties/yml. Passamos como parâmetro da anotação o prefixo usado 
       no application.properties/yml.  

3.9 - @Transactional: Quando um método é anotado com @Transaction ele vai ser executado dentro de uma transação.  

4.10 - @RestController: É uma anotação que tem @Controller e o @ResponseBody.  
     - @RequiredArgsConstructor: Anotação que cria um construtor para os atributos da classe.   

4.13 - Podemos passar por parâmetro na anotação @GetMapping o valor produces. Exemplo: produces = MediaType.APPLICATION_JSON_VALUE, diz que o retorno daquele método anotado com      @GetMapping produz um application/json e também podemos passar MediaType.APPLICATION_XML_VALUE, que diz que retorna um xml.  

4.14 - @PathVariable: Faz com que o parâmetro seguido dessa anotação seja uma variável de path da uri.  

4.15 - @JsonProperty: Com essa anotação conseguimos mudar o nome da representação que aquele atributo vai ter.  
       @JsonIgnore: Com essa anotação a representação ignora o atributo anotado.  
       @JsonRootName: Com essa anotação é possível mudar o nome do root, ou seja do o nome da classe. Usado principalmente para nas representações XML.  

4.16 - @NonNull: Anotação que obrigado a ter um valor no atributo anotado, ele não pode ser nulo.  
       @JacksonXmlRootElement: Essa anotação tem uma propriedade chamada localName, onde conseguimos definir o nome do elemento raiz.  
       @JacksonXmlElementWrapper: Passamos o valor false para o parâmetro useWrapper, que desabilita o embrulho do elemento no xml.  

4.19 - @ResponseStatus: Define qual o status http que o método irá retornar.  


Application Properties Tips:  

server.port - Muda a porta que o TomCat vai rodar a aplicação. Ex: 8080, 8181, 8282.  
spring.profiles.active - Define o perfil que vai estar ativo. Ex: pre, dev, prod. 
spring.application.name - Nome da aplicação.  
spring.datasource.url - Configura a url de conexão que o driver JDBC vai interpretar a url e conectar no banco de dados.  
spring.datasource.username - Define o nome de usuário configurado no banco de dados.  
spring.datasource.password - Define a senha configurada no banco de dados.  
spring.jpa.generate-ddl - Configura o JPA para que ele gere o DDL. DDL é o script de criação das tabelas.  
spring.jpa.hibernate.ddl-auto - Configura o Hibernate para que dependendo do valor executa tais ações no banco de dados. Com o valor =create, diz ao hibernate para apagar e recriar
as tabelas sempre que a aplicação for reiniciada.  
spring.jpa.show-sql - Comando usado para fazer com que exiba no log o comando sql gerado pelo jpa.  
spring.jpa.properties.hibernate.dialect - Configuração para configurar o dialeto do SQL.  



Comandos de linha da comando (PowerShell):
java -jar .\target\algafood-api-0.0.1-SNAPSHOT.jar - Roda o JAR gerado pelo comando maven mvn package.  
java -jar .\target\algafood-api-0.0.1-SNAPSHOT.jar --server.port=8082 - Roda o JAR na porta do servidor 8082.  
java -jar .\target\algafood-api-0.0.1-SNAPSHOT.jar --spring.profiles.active - Muda o perfil da aplicação por linha de comando. 


JPA Anotações:  
@Entity: Anotação em escopo de classe que diz que aquela classe representa uma entidade no banco de dados.  
@Table: Anotação em escopo de classe que pode definir o nome da tabela, passando por parâmetro o name = "Nome da Tabela".  
@Id: Anotação em escopo de atributo que diz que aquele atributo vai representar o identificador da entidade.  
@Column: Anotação em escopo de atributo que serve para definirmos as especificações da coluna. Podemos passar por parâmetro o name="nome da coluna" ou um length=20 para definir o tamanho da coluna.    
@GeneratedValue: Anotação usado no identificador da entidade, serve para definir qual vai ser o gerador de valor. Podemos passar o parâmetro strategy=GenerationType.IDENTITY.  
@ManyToOne: Anotação usada em objetos para referenciar Muitos Pra Um.  
@JoinCoumn: Usada como se fosse o @Column porém em é usado em um atributo que tenho o @ManyToOne junto.  
@Column(nullable = false): Passando o parâmetro nullable = false significa que aquele atributo não pode ser nulo.  

Aulas:
2.8 - JPQL: Linguagem de consulta do JPA, consulta em objetos e não em tabelas.  