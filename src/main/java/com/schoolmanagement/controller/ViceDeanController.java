package com.schoolmanagement.controller;


import com.schoolmanagement.payload.request.ViceDeanRequest;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.ViceDeanResponse;
import com.schoolmanagement.service.ViceDeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vicedean")
@RequiredArgsConstructor
public class ViceDeanController {

    private ViceDeanService viceDeanService;

    //save()***************************************8
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")//vicedean ustu olanlar save yapanilsin
    @PostMapping("/save")
    public ResponseMessage<ViceDeanResponse> save(@RequestBody @Valid ViceDeanRequest viceDeanRequest){

      return   viceDeanService.save(viceDeanRequest);
    }

//updateById()*************************8
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/update/{userId}")
    public ResponseMessage<ViceDeanResponse> update(@RequestBody @Valid ViceDeanRequest viceDeanRequest,
                                                    @PathVariable Long userId){

        return viceDeanService.update(viceDeanRequest,userId);

    }

    //deleteById()******************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping ("/delete/{userId}")
    public ResponseMessage<?> delete(@PathVariable Long userId) {
        return viceDeanService.deleteViceDean( userId);

    }

    //getById*****************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/getViceDeanById/{userId}")
    public ResponseMessage<ViceDeanResponse> getViceDeanById(@PathVariable Long userId) {
        return viceDeanService.getViceDeanById( userId);

    }

    //getAll*****************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/getAll")
    public List<ViceDeanResponse> getAll() {
        return viceDeanService.getAll();

    }

    //getAllWithPage*****************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/search")
    public Page<ViceDeanResponse> getAllWithPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type)
    {
        return viceDeanService.getAllWithPage(page,size,sort,type);

    }




    }
