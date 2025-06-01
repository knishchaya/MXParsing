package com.MX.MXParsing.service;

import com.prowidesoftware.swift.model.AbstractSwiftMessageFactory;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactory;
import com.prowidesoftware.swift.wrappers.saa.v2_0_13.DataPDUParser;
import org.springframework.stereotype.Service;

@Service
public class MxMessageService {

    public String parseMxMessageToJson(String xml) {
    	
        if (xml == null || xml.isEmpty()) {
            throw new IllegalArgumentException("Input XML is null or empty.");
        }
        String jsonOutput = "";
        
        try {
//            String cleanedXml = xml.substring(xml.indexOf("<?xml version="));
        	
        	if(xml.contains("Saa:DataPDU")) {

            DataPDUParser pduParser = DataPDUParser.parse(xml);
            if (pduParser == null) {
                throw new RuntimeException("Failed to parse DataPDU: parser returned null.");
            }

            AbstractMX mxMessage = pduParser.extractMx();
            
            if (mxMessage == null) {
                throw new RuntimeException("Failed to extract MX message: parser returned null.");
            }

            System.out.println("Extracted MX Message:");
            System.out.println(mxMessage.message());

            jsonOutput = mxMessage.toJson();
            System.out.println("Generated JSON:");
            System.out.println(jsonOutput);
        }
        	else {
        		 AbstractMX mxMessage = AbstractMX.parse(xml);
        		 jsonOutput = mxMessage.toJson();
        	}
            return jsonOutput;

        } catch (Exception e) {
            throw new RuntimeException("Error parsing MX message to JSON", e);
        }
    }
    public String convertPacs008ToMt103(String mxXml) throws Exception {
        
        MxPacs00800108 mx = MxPacs00800108.parse(mxXml);
       
        MT103 mt = MT103.parse(mxXml);
        return mt.message();
    }
//    public String convertPacs008ToMt103(String xml) {
//        if (xml == null || xml.isEmpty()) {
//            return "Input message is empty.";
//        }
//
//        try {
//            AbstractMX mxMessage;
//
//            if (xml.contains("Saa:DataPDU")) {
//                // Handle Saa envelope
//            	
//                DataPDUParser pduParser = DataPDUParser.parse(xml);
//                if (pduParser == null) {
//                    return "Failed to parse DataPDU.";
//                }
//
//                mxMessage = pduParser.extractMx();
//                if (mxMessage == null) {
//                    return "Failed to extract MX message.";
//                }
//            } else {
//                mxMessage = AbstractMX.parse(xml);
//                if (mxMessage == null) {
//                    return "Failed to parse MX message.";
//                }
//            }
//
//            // Translate MX to MT
//            Translator<AbstractMX, AbstractMT> translator = TranslatorFactory.getTranslator(mxMessage);
//            if (translator == null) {
//                return "No translator available for MX message type: " + mxMessage.getBusinessProcess();
//            }
//
//            AbstractMT mt = translator.translate(mxMessage);
//            if (mt == null || mt.getSwiftMessage() == null) {
//                return "Translation to MT failed.";
//            }
//
//            // Toggle direction (optional)
//            SwiftMessage toggled = (SwiftMessage) AbstractSwiftMessageFactory.createMessages(mt.message());
//            return toggled.message();
//
//        } catch (Exception e) {
//            return "Error during MX to MT conversion: " + e.getMessage();
//        }
//    }

    
}
