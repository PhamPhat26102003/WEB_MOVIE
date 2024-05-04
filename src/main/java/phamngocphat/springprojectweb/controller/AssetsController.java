package phamngocphat.springprojectweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phamngocphat.springprojectweb.service.impl.StoreService;



@RestController
@RequestMapping("/assets")
public class AssetsController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/{filename:.+}")
    public Resource getResource(@PathVariable("filename") String filename){
        return storeService.loadAsResource(filename);
    }
}
