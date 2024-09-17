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
