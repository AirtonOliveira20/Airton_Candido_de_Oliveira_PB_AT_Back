INSERT INTO fornecedor (nome_fornecedor, cnpj_fornecedor, email_fornecedor, telefone_fornecedor)
VALUES ('Dell Brasil', '07575751000159', 'contato@dell.com.br', '(11) 4004-0000');

INSERT INTO fornecedor (nome_fornecedor, cnpj_fornecedor, email_fornecedor, telefone_fornecedor)
VALUES ('Lenovo Brasil', '01077992000170', 'comercial@lenovo.com.br', '(11) 3003-1000');

INSERT INTO fornecedor (nome_fornecedor, cnpj_fornecedor, email_fornecedor, telefone_fornecedor)
VALUES ('SMS Tecnologia', '92165384000140', 'vendas@sms.com.br', '(41) 2105-2105');

INSERT INTO fornecedor (nome_fornecedor, cnpj_fornecedor, email_fornecedor, telefone_fornecedor)
VALUES ('HP', '92165384000141', 'vendas@hp.com.br', '(41) 2105-2105');

INSERT INTO fornecedor (nome_fornecedor, cnpj_fornecedor, email_fornecedor, telefone_fornecedor)
VALUES ('SAMSUNG', '92165384000140', 'vendas@samsung.com.br', '(41) 2105-2105');

INSERT INTO item
(status_item, numero_patrimonio, nome_item, modelo, numero_serie, voltagem, descricao, num_ri, id_fornecedor)
VALUES
('ATIVO', '0001', 'Notebook Corporativo', 'Dell Latitude 5420', 'SN-DEL-001', '110V', 'Notebook para uso administrativo', '6000', 1);

INSERT INTO item
(status_item, numero_patrimonio, nome_item, modelo, numero_serie, voltagem, descricao, num_ri, id_fornecedor)
VALUES
('MANUTENCAO', '0002', 'Impressora Laser', 'HP LaserJet Pro', 'SN-HP-002', '220V', 'Impressora em manutenção', '6001', 4);

INSERT INTO item
(status_item, numero_patrimonio, nome_item, modelo, numero_serie, voltagem, descricao, num_ri, id_fornecedor)
VALUES
('DESCARTADO', '0003', 'Monitor LED', 'Samsung 24"', 'SN-SAM-003', 'BIVOLT', 'Monitor descartado por defeito', '6003', 5);



INSERT INTO usuario (nome_usuario, email_usuario, login_usuario, senha_usuario,tipo_usuario, status)
VALUES ('Administrador Sistema', 'admin@sistema.com', 'admin', 'admin123', 'admin',true);

INSERT INTO usuario (nome_usuario, email_usuario, login_usuario, senha_usuario, tipo_usuario,status)
VALUES ('João da Silva', 'joao.silva@email.com', 'joao.silva', 'senha123', 'operador',true);

INSERT INTO usuario (nome_usuario, email_usuario, login_usuario, senha_usuario, tipo_usuario, status)
VALUES ('Maria Oliveira', 'maria.oliveira@email.com', 'maria.oliveira', 'senha456', 'operador',false);
