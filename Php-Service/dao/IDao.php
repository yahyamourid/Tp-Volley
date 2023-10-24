<?php 
interface IDao { 
    function create($o); 
    function delete($o); 
    function update($id, $nom, $prenom, $ville, $sexe); 
    function findAll(); 
    function findById($id); 
}