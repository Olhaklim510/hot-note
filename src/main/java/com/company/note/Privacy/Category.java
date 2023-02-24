package com.company.note.Privacy;

import jakarta.persistence.*;

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long privacy_id;

    @Enumerated
    @Column(length = 20)
    private AccessType privacyName;

    public long getPrivacy_id() {
        return privacy_id;
    }

    public void setPrivacy_id(long privacy_id) {
        this.privacy_id = privacy_id;
    }

    public AccessType getPrivacyName() {
        return privacyName;
    }

    public void setPrivacyName(AccessType privacyName) {
        this.privacyName = privacyName;
    }
}
