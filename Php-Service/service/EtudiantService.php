<?php

include_once RACINE . '/classes/Etudiant.php';
include_once RACINE . '/connexion/Connexion.php';
include_once RACINE . '/dao/IDao.php';

class EtudiantService implements IDao {

    private $connexion;

    function __construct() {
        $this->connexion = new Connexion();
    }

    public function create($o) {
        $query = "INSERT INTO Etudiant (`id`, `nom`, `prenom`, `ville`, `sexe`) "
                . "VALUES (NULL, '" . $o->getNom() . "', '" . $o->getPrenom() . "',  
'" . $o->getVille() . "', '" . $o->getSexe() . "');";
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute() or die('Erreur SQL');
    }

    public function delete($id) {
        $query = "delete from Etudiant where id = " . $id;
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute() or die('Erreur SQL');
        return TRUE;
    }

    public function findAll() {
        $etds = array();
        $query = "select * from Etudiant";
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute();
        while ($e = $req->fetch(PDO::FETCH_OBJ)) {
            $etds[] = new Etudiant($e->id, $e->nom, $e->prenom, $e->ville, $e->sexe);
        }
        return $etds;
    }

    public function findById($id) {
        $query = "select * from Etudiant where id = " . $id;
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute();
        if ($e = $req->fetch(PDO::FETCH_OBJ)) {
            $etd = new Etudiant($e->id, $e->nom, $e->prenom, $e->ville, $e->sexe);
            return $etd;
        }
       
    }

    public function update($id,$nom,$prenom,$vill,$sexe) {
        $query = "UPDATE `etudiant` SET `nom` = '" . $nom . "', `prenom` = '" .
                $prenom . "', `ville` = '" . $vill . "', `sexe` = '" .
                $sexe . "' WHERE `etudiant`.`id` = " . $id;
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute() or die('Erreur SQL');
    }

    public function findAllApi() { 
        $query = "select * from Etudiant"; 
        $req = $this->connexion->getConnexion()->prepare($query); 
        $req->execute(); 
        return $req->fetchAll(PDO::FETCH_ASSOC); 
} 
}
