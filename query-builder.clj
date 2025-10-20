(ns query-builder.core)

(defn format-value [v]
  (cond
    (string? v) (str "\"" v "\"")
    (keyword? v) (name v)
    (coll? v) (str "[" (clojure.string/join ", " v) "]")
    :else (str v)))

(def comparadores
  {:igual_a "="
   :maior_que ">"
   :menor_que "<"
   :em "IN"})

(defn compara->sql [m]
  (let [[chave valor] (first (filter #(contains? m (first %)) comparadores))
        campo (:campo m)
        operador (comparadores chave)
        val (get m chave)]
    (if (= operador "IN")
      (str campo " " operador " " (format-value val))
      (str campo " " operador " " (format-value val)))))


(defn e_s [condicoes]
  (let [sqls (map #(if (map? %) (compara->sql %) (%)) condicoes)]
    (fn [] (str "(" (clojure.string/join " AND " sqls) ")"))))

(defn ou_s [condicoes]
  (let [sqls (map #(if (map? %) (compara->sql %) (%)) condicoes)]
    (fn [] (str "(" (clojure.string/join " OR " sqls) ")"))))


(defn filtros [condicao]
  (fn [query]
    (let [where-str (if (map? condicao)
                      (compara->sql condicao)
                      ((condicao)))]
      (assoc query :where where-str))))

(defn campos [lista-campos]
  (fn [query]
    (assoc query :fields (if (seq lista-campos)
                           (clojure.string/join ", " lista-campos)
                           "*"))))

(defn busca_tabela [tabela]
  (fn [& funcoes]
    (let [base {:table tabela :fields "*" :where nil}
          resultado (reduce (fn [q f] (f q)) base funcoes)
          select (str "SELECT " (:fields resultado)
                      " FROM " (:table resultado)
                      (when-let [w (:where resultado)]
                        (str " WHERE " w)))]
      select)))
