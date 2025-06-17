-- Tabela Cliente
CREATE TABLE Cliente (
                         codCliente SERIAL PRIMARY KEY,
                         nomeCliente VARCHAR(100) NOT NULL,
                         rgCliente VARCHAR(9) NOT NULL,
                         enderecoCliente VARCHAR(255) NOT NULL,
                         bairroCliente VARCHAR(100) NOT NULL,
                         cidadeCliente VARCHAR(100) NOT NULL,
                         estadoCliente VARCHAR(2) NOT NULL,
                         CEPCliente VARCHAR(10) NOT NULL,
                         nascimentoCliente DATE NOT NULL
);

-- Tabela Chale
CREATE TABLE Chale (
                       codChale SERIAL PRIMARY KEY,
                       localizacao VARCHAR(255) NOT NULL,
                       capacidade INT NOT NULL,
                       valorAltaEstacao DECIMAL(10, 2) NOT NULL,
                       valorBaixaEstacao DECIMAL(10, 2) NOT NULL
);

-- Tabela Hospedagem
CREATE TABLE Hospedagem (
                            codHospedagem SERIAL PRIMARY KEY,
                            codChale INT NOT NULL,
                            estado VARCHAR(255) NOT NULL,
                            dataInicio DATE NOT NULL,
                            dataFim DATE,
                            qtdPessoas INT NOT NULL,
                            desconto DECIMAL(5, 2),
                            valorFinal DECIMAL(10, 2),
                            codCliente INT,
                            FOREIGN KEY (codChale) REFERENCES Chale(codChale),
                            FOREIGN KEY (codCliente) REFERENCES Cliente(codCliente)
);

-- Tabela Funcionario
CREATE TABLE Funcionario (
                             codFuncionario SERIAL PRIMARY KEY,
                             nomeFuncionario VARCHAR(100) NOT NULL,
                             cpfFuncionario VARCHAR(14) NOT NULL,
                             cargoFuncionario VARCHAR(50) NOT NULL,
                             telefoneFuncionario VARCHAR(20),
                             emailFuncionario VARCHAR(100)
);

-- Tabela Servico
CREATE TABLE Servico (
                         codServico SERIAL PRIMARY KEY,
                         descricao VARCHAR(255) NOT NULL,
                         valor DECIMAL(10, 2) NOT NULL
);

-- Tabela HospedagemServico
CREATE TABLE HospedagemServico (
                                   codHospedagem INT NOT NULL,
                                   codServico INT NOT NULL,
                                   quantidade INT DEFAULT 1,
                                   PRIMARY KEY (codHospedagem, codServico),
                                   FOREIGN KEY (codHospedagem) REFERENCES Hospedagem(codHospedagem),
                                   FOREIGN KEY (codServico) REFERENCES Servico(codServico)
);

-- Tabela FuncionarioServico
CREATE TABLE FuncionarioServico (
                                    codFuncionario INT NOT NULL,
                                    codServico INT NOT NULL,
                                    PRIMARY KEY (codFuncionario, codServico),
                                    FOREIGN KEY (codFuncionario) REFERENCES Funcionario(codFuncionario),
                                    FOREIGN KEY (codServico) REFERENCES Servico(codServico)
);