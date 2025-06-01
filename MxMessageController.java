package com.MX.MXParsing.controller;


import com.MX.MXParsing.service.MxMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mx")
@CrossOrigin(origins = "*")
public class MxMessageController {

    private final MxMessageService mxMessageService = new MxMessageService();

    //    @PostMapping("/parse")
    @PostMapping(value = "/parse", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE })
    public String parseMxMessage(@RequestBody String mxMessage) {
        return mxMessageService.parseMxMessageToJson(mxMessage);
    }
    @PostMapping(value = "/convert", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String convertMxToMt(@RequestBody String mxMessage) throws Exception {
        return mxMessageService.convertPacs008ToMt103(mxMessage);
    }
//    @PostMapping("/create")
//    public String createMxMessage(@RequestBody String jsonRequest) {
//        return mxMessageService.createMxMessageFromJson(jsonRequest);
//    }
}
