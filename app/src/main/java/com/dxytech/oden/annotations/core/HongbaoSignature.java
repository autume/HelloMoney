package com.dxytech.oden.annotations.core;

import android.view.accessibility.AccessibilityNodeInfo;

import com.dxytech.oden.annotations.app.utils.L;


public class HongbaoSignature {
    private String sender, content, time;

    public boolean generateSignature(AccessibilityNodeInfo node) {
        try {
            AccessibilityNodeInfo hongbaoNode = node.getParent();
            String hongbaoContent = hongbaoNode.getChild(0).getText().toString();

            if (hongbaoContent == null) return false;
            L.d("hongbaoNode: " + hongbaoNode.toString());
            L.d("hongbaoNode.isClickable(): " + hongbaoNode.isClickable());
            AccessibilityNodeInfo messageNode = hongbaoNode.getParent();

            String[] hongbaoInfo = getSenderContentDescriptionFromNode(messageNode);

            if (this.getSignature(hongbaoInfo[0], hongbaoContent, hongbaoInfo[1]).equals(this.toString())) {
                L.d("already picked!");
                return false;
            }

            this.sender = hongbaoInfo[0];
            this.time = hongbaoInfo[1];
            this.content = hongbaoContent;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getSignature(this.sender, this.content, this.time);
    }

    private String getSignature(String... strings) {
        String signature = "";
        for (String str : strings) {
            if (str == null) return null;
            signature += str + "|";
        }

        return signature.substring(0, signature.length() - 1);
    }

    private String[] getSenderContentDescriptionFromNode(AccessibilityNodeInfo node) {
        int count = node.getChildCount();
        L.d("count: " + count);
        String[] result = {"unknownSender", "unknownTime"};
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo thisNode = node.getChild(i);
//            if (thisNode.getContentDescription() != null){
//                L.d("getSenderContentDescriptionFromNode: " + thisNode.getContentDescription().toString());
//            }
//            if (thisNode.toString() != null)
//                L.d("thisNode: " + thisNode.toString());
            if ("android.widget.ImageView".equals(thisNode.getClassName())) {
                CharSequence contentDescription = thisNode.getContentDescription();
                if (contentDescription != null) result[0] = contentDescription.toString();
            } else if ("android.widget.TextView".equals(thisNode.getClassName())) {
                CharSequence thisNodeText = thisNode.getText();
                if (thisNodeText != null) result[1] = thisNodeText.toString();
            }
        }

//        L.d("------------------------->");
//        AccessibilityNodeInfo testNode = node.getParent();
//        L.d("testNode count: " + testNode.getChildCount());
//        for (int i = 0; i < testNode.getChildCount(); i++) {
//            AccessibilityNodeInfo testNodeChild = testNode.getChild(i);
//            L.d("testNodeChild.getChildCount(): " + testNodeChild.getChildCount());
//            for (int j = 0; j < testNodeChild.getChildCount(); j++) {
//                if (testNodeChild.getChild(j).getContentDescription() != null)
//                    L.d("testNodeChild: " + j + ",:"+ testNodeChild.getChild(j).getContentDescription().toString());
//                if (testNodeChild.getChild(j).toString() != null)
//                    L.d("testNodeChild: " + j + ",:"+ testNodeChild.getChild(j).toString());
//
//                L.d("testNodeChild: " + j + ",:" + testNode.getChild(j).toString());
//            }
//        }
//        L.d("<-------------------------");

        return result;
    }
}
