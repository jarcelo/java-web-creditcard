
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="ajax.js" type="text/javascript"></script>
<script src="history.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
    function pageAction(action)
    {
        document.card.actiontype.value=action;
        if (ajax && action === 'history') {
            ajax.open('get','AccountAction?actiontype=history');
            ajax.send(null);
        } else {
            document.card.submit();
        }
    }
    </script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
            integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Credit Card</title>
    </head>
    <body>
        <div class="container">
            <h1 class="text-danger text-center">Credit Card Simulator</h1>
            <hr>
            <form class="form-horizontal" action="AccountAction" name="card" id="card" method="post">
                
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="">Account ID</label>
                    <div class="col-sm-3">
                         <input class="form-control" type="text" name="account" id="account" 
                       value="${empty card.accountId ? cookie.acct.value : card.accountId}"/>
                    </div>
                    <input class="btn btn-default" type="submit" value="New Account" onclick="pageAction('new')">
                    <input class="btn btn-default" type="submit" value="&nbsp;&nbsp;&nbsp;&nbsp;Existing&nbsp;&nbsp;&nbsp;&nbsp;" onclick="pageAction('existing')">
                </div>
                    
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="cAmt">Charge Amount</label>
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-addon">$</div>
                            <input class="form-control" type="text" name="cAmt" id="cAmt">
                            <div class="input-group-addon">.00</div>
                        </div>
                    </div>
                    <label class="col-sm-1 control-label" for="cDesc">Description</label>
                    <div class="col-sm-3">
                        <input class="form-control" type="text" name="cDesc" id="cDesc" size="40">
                    </div>
                    <input class="btn btn-default" type="button" name="charge" id="charge" value="Post Charge" onclick="pageAction('charge')">
                </div>
                
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="">Payment Amount</label>
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-addon">$</div>
                            <input class="form-control" type="text" name="pAmt" id="pAmt">
                            <div class="input-group-addon">.00</div>
                        </div>
                    </div>
                    <input class="btn btn-default" type="button" name="payment" id="payment" value="Post Payment" onclick="pageAction('payment')">
                </div>
                    
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="">Credit Increase</label>
                    <div class="col-sm-3">
                        <div class="input-group">
                            <div class="input-group-addon">$</div>                           
                            <input class="form-control" type="text" name="cIncrease" id="cIncrease" size="15">
                            <div class="input-group-addon">.00</div>                            
                        </div>
                    </div>
                    <input class="btn btn-default" type="button" name="Increase" id="Increase" value="Credit Increase" onclick="pageAction('increase')">
                </div>
                    
                <div class="form-group">
                    <label class="col-sm-2 control-label">Interest Rate</label>
                    <div class="col-sm-3">
                        <input class="form-control" type="text" name="iRate" id="iRate">
                    </div>
                    <input class="btn btn-default" type="button" name="interest" id="interest" value="Interest Charge" onclick="pageAction('interest')">
                </div>
                
                <hr>

                <div class="form-group">
                       <input class="btn btn-primary" type="button" name="history" id="history" value="Display History" onclick="pageAction('history')">
                </div>
                    
                <br>
                <hr><br>
                <table border="1">
                    <tr>
                        <td>Bank Balance:</td>
                        <td><input type="text" name="bbal" id="bbal" readonly
                                   value=""/></td>
                    </tr>
                    <tr>
                        <td>Credit Limit:</td>
                        <td><input type="text" name="climit" id="climit" readonly
                                   value="${card.creditLimit}"/></td>
                    </tr>
                    <tr>
                        <!--align right-->
                        <td>Balance Due:</td>
                        <td><input type="text" name="cbal" id="cbal" readonly
                                   value="${card.outstandingBal}"/></td>
                    </tr>
                    <tr>
                        <td>Available Credit:</td>
                        <td><input type="text" name="availablecr" id="availablecr" readonly
                                   value="${card.availableCr}"/></td>
                    </tr>
                </table>
                <input type="hidden" name="actiontype" id="actiontype" value="">
            </form>
            <br>

            <div id="results"></div>

            <div>
                <p>${errorMessage}</p>
            </div>
            <div>
                <%
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for(Cookie c : cookies) {

                %>
                <%= c.getName() %> = <%= c.getValue() %> <br>
                <%
                        }
                    }
                %>
            </div>
        </div> <!-- .container -->
    </body>
</html>
