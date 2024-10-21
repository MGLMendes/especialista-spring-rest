-- Cozinha
insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');

-- Restaurante
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1)
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1)
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2)

-- Estado
insert into estado (nome) values ('Sao Paulo')
insert into estado (nome) values ('Rio de Janeiro')
insert into estado (nome) values ('Belo Horizonte')
insert into estado (nome) values ('Espirito Santo')

-- Cidade
insert into cidade (nome, estado_id) values ('Sao Paulo', 1)
insert into cidade (nome, estado_id) values ('Rio de Janeiro', 2)
insert into cidade (nome, estado_id) values ('Belo Horizonte', 3)
insert into cidade (nome, estado_id) values ('Vitoria', 4)

-- Forma Pagamento
insert into forma_pagamento (descricao) values ('Cartao De Crédito')
insert into forma_pagamento (descricao) values ('Cartao De Débito')
insert into forma_pagamento (descricao) values ('PIX')

-- Permissao
insert into permissao (nome, descricao) values ('Consulta Produtos', 'Permite a consulta dos produtos')
insert into permissao (nome, descricao) values ('Consulta Restaurantes', 'Permite a consulta dos restaurantes')

-- Forma Pagamento
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,1), (2,2),(2,3),(3,1),(3,2),(3,3)