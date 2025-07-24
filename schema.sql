
-- TABELA CLIENTE
CREATE TABLE Cliente (
                         codCliente SERIAL PRIMARY KEY,
                         nomeCliente VARCHAR(100) NOT NULL,
                         enderecoCliente VARCHAR(255) NOT NULL,
                         bairroCliente VARCHAR(100) NOT NULL,
                         cidadeCliente VARCHAR(100) NOT NULL,
                         estadoCliente CHAR(2) NOT NULL,
                         cepCliente CHAR(10) NOT NULL,
                         nascimentoCliente DATE NOT NULL
);

-- TABELA CHALE
CREATE TABLE Chale (
                       codChale SERIAL PRIMARY KEY,
                       localizacao VARCHAR(255) NOT NULL,
                       capacidade INT NOT NULL CHECK (capacidade > 0),
                       valorAltaEstacao NUMERIC(10, 2) NOT NULL CHECK (valorAltaEstacao >= 0),
                       valorBaixaEstacao NUMERIC(10, 2) NOT NULL CHECK (valorBaixaEstacao >= 0),
                       status VARCHAR(50) DEFAULT 'disponivel' CHECK (status IN ('disponivel', 'ocupado'))
);

-- TABELA FUNCIONARIO
CREATE TABLE Funcionario (
                             codFuncionario SERIAL PRIMARY KEY,
                             nomeFuncionario VARCHAR(100) NOT NULL,
                             cpfFuncionario VARCHAR(14) NOT NULL UNIQUE,
                             cargoFuncionario VARCHAR(50) NOT NULL,
                             telefoneFuncionario VARCHAR(20),
                             emailFuncionario VARCHAR(100) UNIQUE
);

-- TABELA SERVICO
CREATE TABLE Servico (
                         codServico SERIAL PRIMARY KEY,
                         descricao VARCHAR(255) NOT NULL,
                         valor NUMERIC(10, 2) NOT NULL CHECK (valor >= 0)
);

-- TABELA HOSPEDAGEM
CREATE TABLE Hospedagem (
                            codHospedagem SERIAL PRIMARY KEY,
                            codChale INT NOT NULL,
                            codCliente INT NOT NULL,
                            dataInicio DATE NOT NULL,
                            dataFim DATE NOT NULL CHECK (dataFim >= dataInicio),
                            qtdPessoas INT NOT NULL CHECK (qtdPessoas > 0),
                            desconto NUMERIC(5, 2) DEFAULT 0 CHECK (desconto >= 0),
                            valorDiaria NUMERIC(10, 2) NOT NULL CHECK (valorDiaria >= 0),
                            valorTotal NUMERIC(10, 2) GENERATED ALWAYS AS (
                                ((EXTRACT(day FROM (dataFim::timestamp - dataInicio::timestamp)) + 1) * valorDiaria - desconto)
                                ) STORED,
                            status VARCHAR(20) NOT NULL DEFAULT 'ativa' CHECK (status IN ('ativa', 'cancelada', 'finalizada')),
                            FOREIGN KEY (codChale) REFERENCES Chale(codChale) ON DELETE CASCADE,
                            FOREIGN KEY (codCliente) REFERENCES Cliente(codCliente) ON DELETE CASCADE
);

-- TABELA LOG HOSPEDES
CREATE TABLE log_hospedes (
                              id SERIAL PRIMARY KEY,
                              hospede_id INT NOT NULL,
                              acao VARCHAR(50) NOT NULL,
                              data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (hospede_id) REFERENCES Cliente(codCliente) ON DELETE CASCADE
);

-- TABELA HOSPEDAGEM_SERVICO
CREATE TABLE HospedagemServico (
                                    codHospedagem INT NOT NULL,
                                    codServico INT NOT NULL,
                                    quantidade INT DEFAULT 1 CHECK (quantidade > 0),
                                    PRIMARY KEY (codHospedagem, codServico),
                                    FOREIGN KEY (codHospedagem) REFERENCES Hospedagem(codHospedagem) ON DELETE CASCADE,
                                    FOREIGN KEY (codServico) REFERENCES Servico(codServico) ON DELETE CASCADE
);

-- TABELA FUNCIONARIO_SERVICO
CREATE TABLE FuncionarioServico (
                                     codFuncionario INT NOT NULL,
                                     codServico INT NOT NULL,
                                     PRIMARY KEY (codFuncionario, codServico),
                                     FOREIGN KEY (codFuncionario) REFERENCES Funcionario(codFuncionario) ON DELETE CASCADE,
                                     FOREIGN KEY (codServico) REFERENCES Servico(codServico) ON DELETE CASCADE
);

-- FUNÇÃO: VALOR TOTAL GASTO
CREATE OR REPLACE FUNCTION valor_total_gasto(hospede_id INT)
    RETURNS NUMERIC AS $$
DECLARE
    total NUMERIC;
BEGIN
    SELECT COALESCE(SUM(h.valorTotal), 0) + COALESCE(SUM(s.valor * hs.quantidade), 0) INTO total
    FROM Hospedagem h
             LEFT JOIN HospedagemServico hs ON h.codHospedagem = hs.codHospedagem
             LEFT JOIN Servico s ON hs.codServico = s.codServico
    WHERE h.codCliente = hospede_id AND h.status != 'cancelada';

    RETURN total;
END;
$$ LANGUAGE plpgsql;

-- FUNÇÃO: ATUALIZAR STATUS CHALE
CREATE OR REPLACE FUNCTION atualizar_status_chale()
    RETURNS TRIGGER AS $$
BEGIN
    IF CURRENT_DATE BETWEEN NEW.dataInicio AND NEW.dataFim THEN
        UPDATE Chale
        SET status = 'ocupado'
        WHERE codChale = NEW.codChale;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_atualizar_status_chale
    AFTER INSERT ON Hospedagem
    FOR EACH ROW
EXECUTE FUNCTION atualizar_status_chale();

-- FUNÇÃO: LIBERAR CHALE AUTOMATICAMENTE
CREATE OR REPLACE FUNCTION liberar_chale_automaticamente()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'finalizada' THEN
        UPDATE Chale
        SET status = 'disponivel'
        WHERE codChale = OLD.codChale;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGER: LIBERAR CHALE AUTOMATICAMENTE
CREATE TRIGGER trigger_liberar_chale
    AFTER UPDATE ON Hospedagem
    FOR EACH ROW
    WHEN (OLD.status != 'finalizada' AND NEW.status = 'finalizada')
EXECUTE FUNCTION liberar_chale_automaticamente();

-- Liberar chale (botão)
CREATE OR REPLACE PROCEDURE liberar_chale(cod_chale INT)
    LANGUAGE plpgsql AS $$
BEGIN
    UPDATE Chale
    SET status = 'disponivel'
    WHERE codChale = cod_chale;
    Update Hospedagem
    SET status = 'finalizada'
    WHERE codChale = cod_chale
      AND status = 'ativa';
END;
$$;

-- VIEW: HOSPEDES ATIVOS
CREATE OR REPLACE VIEW hospedes_ativos AS
SELECT DISTINCT c.*
FROM Cliente c
         JOIN Hospedagem h ON h.codCliente = c.codCliente
WHERE h.status = 'ativa'
  AND CURRENT_DATE BETWEEN h.dataInicio AND h.dataFim;

-- INDICES
CREATE INDEX idx_cliente_nome ON Cliente(nomeCliente);
CREATE INDEX idx_chale_status ON Chale(status);
CREATE INDEX idx_hospedagem_cliente ON Hospedagem(codCliente);
CREATE INDEX idx_hospedagem_chale ON Hospedagem(codChale);