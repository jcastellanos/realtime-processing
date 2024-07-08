import React, { useEffect, useState } from "react";
import { Table } from 'react-bootstrap';

const Realtime = () => {
  const [stockData, setStockData] = useState([]);

  useEffect(() => {
    // opening a connection to the server to begin receiving events from it
    const eventSource = new EventSource("http://localhost:8080/realtime/sse");
    
    // attaching a handler to receive message events
    eventSource.onmessage = (event) => {
      const result = JSON.parse(event.data);
      //stockData.push(result);
      // setStockData({ ...stockData });
      setStockData((prevEvents) => [result, ...prevEvents]);
      
    };
    
    // terminating the connection on component unmount
    return () => eventSource.close();
  }, []);

  return (
    <div>
        <h5>Realtime Transactions</h5>
        <Table striped bordered hover>
        <thead>
            <tr>
            <th>Time</th>
            <th>User</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Location</th>
            </tr>
        </thead>
        <tbody>
            {stockData.map(artist => (
            <tr key={artist.transaction_id}>
                <td>{artist.txtime}</td>
                <td>{artist.user}</td>
                <td>{artist.amount}</td>
                <td>{artist.status}</td>
                <td>{artist.location}</td>
            </tr>
            ))}
        </tbody>
        </Table>
    </div>
  );
};

export default Realtime;