import React, { useEffect, useState } from "react";
import { FileEarmarkText } from 'react-bootstrap-icons';

const Summary = () => {
  const [stockData, setStockData] = useState({});
  const [updated, setUpdated] = useState(false);

  useEffect(() => {
    // opening a connection to the server to begin receiving events from it
    const eventSource = new EventSource("http://localhost:8082/summary/sse");
    
    // attaching a handler to receive message events
    eventSource.onmessage = (event) => {
      const result = JSON.parse(event.data);
      //stockData.push(result);
      // setStockData({ ...stockData });
      setStockData(result);
      setUpdated(true);
      setTimeout(() => setUpdated(false), 1500);
    };
    
    // terminating the connection on component unmount
    return () => eventSource.close();
  }, []);

  return (
    <div className="container-fluid">
        <div className="row">
            <div className="col-md-3 p-1" > 
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Transactions:</p>
                        <p class="h3">{stockData.transactionsCount}</p>
                    </div>
                </div>
            </div>
            <div className="col-md-3 p-1">
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Successful:</p>
                        <p class="h3">{stockData.transactionsSuccessfulCount}</p>
                    </div>
                </div>
            </div>
            <div className="col-md-3 p-1">
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Failed:</p>
                        <p class="h3">{stockData.transactionsFailedCount}</p>
                    </div>
                </div>
            </div>
            <div className="col-md-3 p-1">
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Total Amount:</p>
                        <p class="h3">{stockData.totalAmount}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
  );
};

export default Summary;