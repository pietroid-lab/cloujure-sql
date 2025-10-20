# 🧩 SQL Query Builder em Clojure

Este projeto implementa um **Query Builder funcional** em **Clojure**, permitindo construir consultas SQL de forma composicional, utilizando funções puras e de alta ordem — sem a necessidade de escrever SQL diretamente.

---

## 🚀 Objetivo

Demonstrar o uso de **funções puras**, **funções de alta ordem**, **map**, **reduce** e **currying** para compor consultas SQL de forma funcional e declarativa.

---

## 🧠 Conceito

A ideia é montar consultas SQL encadeando funções Clojure que representam partes da query:

```clojure
(busca_tabela "usuario")
  (campos ["nome" "idade"])
  (filtros
    (e_s
      [
        {:campo "nome" :igual_a "José"}
        {:campo "idade" :maior_que 20}
        (ou_s [{:campo "status" :igual_a true}
               {:campo "status" :igual_a false}])
      ]))

Resultado      

SELECT nome, idade FROM usuario
WHERE (nome = "José" AND idade > 20 AND (status = true OR status = false))


## ⚙️ Como executar

1 Certifique-se de ter o Leiningen ou Clojure CLI instalado.

Instalar Leiningen

Instalar Clojure CLI

2 Clone o repositório:

git clone https://github.com/seuusuario/query-builder-clojure.git
cd query-builder-clojure


3 Abra o REPL e rode o exemplo:

clj -M -r