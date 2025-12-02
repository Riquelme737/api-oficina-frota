# 🔧 API Oficina de Frota - Gestão Inteligente de Ferramentas

Sistema de backend desenvolvido com **Spring Boot** para o rastreamento, controle e gestão de empréstimos de ferramentas em uma oficina mecânica. O projeto inclui um módulo de inteligência de dados que exporta históricos para análise preditiva de atrasos usando Machine Learning.

## 🚀 Funcionalidades

* **Controle de Empréstimos:** Check-in (retirada) e Check-out (devolução) com validação de status.
* **Regras de Negócio:** Impede empréstimo de ferramentas já em uso; bloqueio de ferramentas danificadas.
* **Cálculo Automático:** O sistema define automaticamente se houve atraso (baseado em regra de 8 horas).
* **Data Seeding:** O banco de dados inicia populado com **300 registros realistas** (nomes, ferramentas, datas retroativas) para testes imediatos.
* **Integração Data Science:** Geração dinâmica de relatórios `.csv` com *Feature Engineering* (cálculo de duração) para alimentar modelos de IA.

## 🛠️ Tecnologias Utilizadas

* **Java 21** & **Spring Boot 3**
* **Spring Data JPA** (Hibernate)
* **H2 Database** (Banco em memória para testes rápidos)
* **JUnit 5 & Mockito** (Testes Unitários)
* **Python/Pandas/Scikit-Learn** (Para análise de dados do CSV gerado)

---

## 📦 Como Rodar a Aplicação

### Pré-requisitos
* Java 17 instalado.
* Maven (ou use o wrapper `mvnw` incluso).

### Passo a Passo
1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/api-oficina-frota.git](https://github.com/seu-usuario/api-oficina-frota.git)
    cd api-oficina-frota
    ```

2.  **Execute o projeto:**
    * **Linux/Mac:** `./mvnw spring-boot:run`
    * **Windows:** `.\mvnw.cmd spring-boot:run`

3.  **Acesse:** A API estará rodando em `http://localhost:8080`.

---

## 📡 Endpoints Principais (Documentação)

### 1. 📋 Gerar Relatório para IA (Supervisor)
Baixa um arquivo `.csv` contendo o histórico de 300 empréstimos, formatado para treinamento de modelos de Machine Learning (KNN).
* **Método:** `GET`
* **URL:** `http://localhost:8080/supervisor/relatorio/csv`
* **Retorno:** Arquivo `relatorio_historico_fechado.csv`.

### 2. 🛠️ Realizar Empréstimo (Check-in)
* **Método:** `POST`
* **URL:** `http://localhost:8080/emprestimos`
* **Body (JSON):**
    ```json
    {
        "idOperador": "uuid-do-operador",
        "idFerramenta": "uuid-da-ferramenta",
        "idOs": "uuid-da-ordem-servico",
        "turno": "MANHA"
    }
    ```

### 3. ↩️ Realizar Devolução (Check-out)
O sistema calculará automaticamente se está `NORMAL` ou `ATRASADO`.
* **Método:** `PUT`
* **URL:** `http://localhost:8080/emprestimos/devolucao`
* **Body (JSON):**
    ```json
    {
        "idEmprestimo": "uuid-do-emprestimo-pendente",
    }
    ```

---

## 🧪 Como Rodar os Testes

O projeto possui cobertura de testes unitários validando as regras de negócio (bloqueio de ferramenta em uso, cálculo de datas, geração de CSV).

Execute no terminal:
```bash
./mvnw test
```

---

## 📊 Análise de Dados e IA

O projeto foi validado com um script Python (Jupyter Notebook) que consome o CSV gerado pelo Backend.
1. O Backend gera o CSV com a coluna calculada duracao_horas.
2. O Modelo (KNN) treinado com esses dados alcançou 92% de acurácia na previsão de atrasos.

---

## 📂 Estrutura do Projeto

````
src/main/java/com/unifacs/ads/api_oficina_frota
├── config      # Configuração de Seeding (TestConfig)
├── controller  # Endpoints REST
├── dto         # Objetos de Transferência de Dados (Records)
├── model       # Entidades JPA e Regras de Domínio (RelatorioModel)
├── repository  # Interfaces de Banco de Dados
└── service     # Lógica de Negócios (Emprestimos, Supervisor)
````
