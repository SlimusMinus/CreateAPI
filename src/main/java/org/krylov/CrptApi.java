package org.krylov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.concurrent.Semaphore;

public class CrptApi {
    private Semaphore semaphore;
    private int requestsLimit;
    private long interval;

    public CrptApi(int requestsLimit, long interval) {
        this.requestsLimit = requestsLimit;
        this.interval = interval;
        this.semaphore = new Semaphore(requestsLimit);
    }

    public class Document {
        class Description {
            String participantInn;

            public String getParticipantInn() {
                return participantInn;
            }

            public void setParticipantInn(String participantInn) {
                this.participantInn = participantInn;
            }
        }

        String doc_id, doc_status, doc_type, owner_inn, participant_inn, producer_inn, production_type;
        int LP_INTRODUCE_GOODS;
        boolean importRequest;
        Date production_date;

        public String getDoc_id() {
            return doc_id;
        }

        public void setDoc_id(String doc_id) {
            this.doc_id = doc_id;
        }

        public String getDoc_status() {
            return doc_status;
        }

        public void setDoc_status(String doc_status) {
            this.doc_status = doc_status;
        }

        public String getDoc_type() {
            return doc_type;
        }

        public void setDoc_type(String doc_type) {
            this.doc_type = doc_type;
        }

        public String getOwner_inn() {
            return owner_inn;
        }

        public void setOwner_inn(String owner_inn) {
            this.owner_inn = owner_inn;
        }

        public String getParticipant_inn() {
            return participant_inn;
        }

        public void setParticipant_inn(String participant_inn) {
            this.participant_inn = participant_inn;
        }

        public String getProducer_inn() {
            return producer_inn;
        }

        public void setProducer_inn(String producer_inn) {
            this.producer_inn = producer_inn;
        }

        public String getProduction_type() {
            return production_type;
        }

        public void setProduction_type(String production_type) {
            this.production_type = production_type;
        }

        public int getLP_INTRODUCE_GOODS() {
            return LP_INTRODUCE_GOODS;
        }

        public void setLP_INTRODUCE_GOODS(int LP_INTRODUCE_GOODS) {
            this.LP_INTRODUCE_GOODS = LP_INTRODUCE_GOODS;
        }

        public boolean isImportRequest() {
            return importRequest;
        }

        public void setImportRequest(boolean importRequest) {
            this.importRequest = importRequest;
        }

        public Date getProduction_date() {
            return production_date;
        }

        public void setProduction_date(Date production_date) {
            this.production_date = production_date;
        }

        class Products {
            String certificate_document, certificate_document_number, owner_inn, producer_inn, tnved_code, uit_code, uitu_code;
            Date certificate_document_date, production_date;

            public String getCertificate_document() {
                return certificate_document;
            }

            public void setCertificate_document(String certificate_document) {
                this.certificate_document = certificate_document;
            }

            public String getCertificate_document_number() {
                return certificate_document_number;
            }

            public void setCertificate_document_number(String certificate_document_number) {
                this.certificate_document_number = certificate_document_number;
            }

            public String getOwner_inn() {
                return owner_inn;
            }

            public void setOwner_inn(String owner_inn) {
                this.owner_inn = owner_inn;
            }

            public String getProducer_inn() {
                return producer_inn;
            }

            public void setProducer_inn(String producer_inn) {
                this.producer_inn = producer_inn;
            }

            public String getTnved_code() {
                return tnved_code;
            }

            public void setTnved_code(String tnved_code) {
                this.tnved_code = tnved_code;
            }

            public String getUit_code() {
                return uit_code;
            }

            public void setUit_code(String uit_code) {
                this.uit_code = uit_code;
            }

            public String getUitu_code() {
                return uitu_code;
            }

            public void setUitu_code(String uitu_code) {
                this.uitu_code = uitu_code;
            }

            public Date getCertificate_document_date() {
                return certificate_document_date;
            }

            public void setCertificate_document_date(Date certificate_document_date) {
                this.certificate_document_date = certificate_document_date;
            }

            public Date getProduction_date() {
                return production_date;
            }

            public void setProduction_date(Date production_date) {
                this.production_date = production_date;
            }
        }
        Date reg_date;
        String reg_number;

        public Date getReg_date() {
            return reg_date;
        }

        public void setReg_date(Date reg_date) {
            this.reg_date = reg_date;
        }

        public String getReg_number() {
            return reg_number;
        }

        public void setReg_number(String reg_number) {
            this.reg_number = reg_number;
        }
    }


    public void createDocument(Document document, String signature) {
        try {
            semaphore.acquire();
            Thread.sleep(1000);

            System.out.println("Document: " + document.toString());
            System.out.println(signature);

            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        final CrptApi api = new CrptApi(5, 1000);

        final String signature = "signature";
        ObjectMapper objectMapper = new ObjectMapper();
        String URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
        final Document document = objectMapper.readValue(URL, Document.class);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    api.createDocument(document, signature);
                }
            });
            thread.start();
        }
    }
}
