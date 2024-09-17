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