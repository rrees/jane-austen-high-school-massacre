(ns jane-austen-high-school-massacre.core)



(def high-school (atom {:characters [{:name :emma     :status (rand-nth (range -5 5))}
                                     {:name :mr-darcy :status (rand-nth (range -5 5))}
                                     {:name :lizzie   :status 3}]}))

(defn update-status [game character change]
  (let [characters (:characters game)]
      (map #(if (= character (:name %)) (update-in % [:status] (partial + change)) %) characters)))
   
(defn social-action [person verb change]
  (str verb ": " person)
  (swap! high-school assoc :characters (update-status @high-school person change))
  (social-status))

(defn snub
  [person]
  (social-action person "Snub" -3))

(defn praise
  [person]
  (social-action person "Praise" 3))

(defn diss
  [person]
  (social-action person "Diss" -10))

(defn flirt
  [person]
  (social-action person "Flirt" (- (rand-int 15) 8)))

(defn friends []
  (->> @high-school 
    :characters
    (filter #(> (:status %) 0))
    (map :name)))


(defn enemies []
  (->> @high-school
    :characters
    (filter #(<= (:status %) 0))
    (map :name)))

(defn redrum? []
  (= (count (enemies)) (count (:characters @high-school))))

(defn social-status []
  (if (redrum?)
    (println "Everyone hates you! You've been brutally massacred and eaten!")
    (println "Your friends are "(clojure.string/join " " friends))))
  
(println "You're at school with :emma, :lizzie and :mr-darcy... use snub, praise, diss and flirt to try to make everyone like you")
