package org.example.vofasbackendv1.messaging.message;

import lombok.Data;
import org.example.vofasbackendv1.data_layer.enums.FeedbackTypeEnum;

import java.io.Serializable;

@Data
public class FeedbackProcessingMessage implements Serializable {
    private Long feedbackId;

    private FeedbackTypeEnum feedbackType;

    //Artifact is either file path or plain text to be processed. The distinction made by feedbackType
    private String artifact;
}
